package rain.snmp4j;

import org.apache.commons.collections4.CollectionUtils;
import org.snmp4j.*;
import org.snmp4j.mp.MessageProcessingModel;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.PDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import java.io.IOException;
import java.util.List;

// 需要在 被监控的主机上 开启 “snmp服务”
// 例如：    有3台主机 （centre ，A，B）
//          需要在centre主机上监控AB主机的cpu内存情况
//          需要 A、B 主机上 开启 “snmp服务”
// “snmp服务” 没有的 需要安装
public class SNMP4j {

    public static String ip = "10.88.254.222";

    public static void main(String[] args) {
        collectCPU();

        collectMemory();

//        collectComputerInfo();
    }

    public static void collectCPU() {
        String[] oids = {"1.3.6.1.2.1.25.3.3.1.2"};

        findInfo(events -> {
            int percentage = 0;
            for (TableEvent event : events) {
                VariableBinding[] values = event.getColumns();
                if (values == null) continue;

                percentage += Integer.parseInt(values[0].getVariable().toString());
            }
            System.out.println("CPU利用率为：" + percentage / events.size() + "%");
        }, oids);
    }

    //获取内存相关信息
    public static void collectMemory() {
        String[] oids = {"1.3.6.1.2.1.25.2.3.1.2",  //type 存储单元类型
                "1.3.6.1.2.1.25.2.3.1.3",  //descr
                "1.3.6.1.2.1.25.2.3.1.4",  //unit 存储单元大小
                "1.3.6.1.2.1.25.2.3.1.5",  //size 总存储单元数
                "1.3.6.1.2.1.25.2.3.1.6"}; //used 使用存储单元数;

        findInfo(events -> {
            for (TableEvent event : events) {
                VariableBinding[] values = event.getColumns();
                if (values == null) return;

                int unit = Integer.parseInt(values[2].getVariable().toString());//unit 存储单元大小
                int totalSize = Integer.parseInt(values[3].getVariable().toString());//size 总存储单元数
                int usedSize = Integer.parseInt(values[4].getVariable().toString());//used  使用存储单元数
                String s = (long) totalSize * unit / (1024 * 1024 * 1024) + "G   内存使用率为：" + (long) usedSize * 100 / totalSize + "%";

                String PHYSICAL_MEMORY_OID = "1.3.6.1.2.1.25.2.1.2";//物理存储
                String VIRTUAL_MEMORY_OID = "1.3.6.1.2.1.25.2.1.3"; //虚拟存储
                String oid = values[0].getVariable().toString();
                if (PHYSICAL_MEMORY_OID.equals(oid)) {
                    System.out.println("PHYSICAL_MEMORY -----> 物理内存大小：" + s);
                } else if (VIRTUAL_MEMORY_OID.equals(oid)) {
                    System.out.println("VIRTUAL_MEMORY -----> 虚拟内存大小：" + s);
                }
            }
        }, oids);
    }

    public static void collectComputerInfo() {
        String[] oids = {"1.3.6.1.2.1.1.1",  //type 存储单元类型
                "1.3.6.1.2.1.1.2"}; //used 使用存储单元数;

        findInfo(events -> {
            for (TableEvent event : events) {
                VariableBinding[] values = event.getColumns();
                if (values == null) return;

                VariableBinding vb1 = values[0];
                VariableBinding vb2 = values[1];
                System.out.println(vb1);
                System.out.println(vb2);
            }
        }, oids);
    }

    // 1、先定义函数式接口
    // 意味着 可以将行为（即方法体）变成参数
    @FunctionalInterface
    public interface TableEventInterface {
        void process(List<TableEvent> events);
    }

    // 2、将想隐藏的代码放在新方法中，通过函数接口去调用
    public static void findInfo(TableEventInterface classFindInterface, String[] oids) {
        TransportMapping transport = null;
        Snmp snmp = null;
        try {
            transport = new DefaultUdpTransportMapping();
            transport.listen();//监听消息
            snmp = new Snmp(transport);//创建snmp
            CommunityTarget target = buildCommunityTarget();
            TableUtils tableUtils = buildTableUtils(snmp);
            OID[] columns = buildOids(oids);
            List<TableEvent> events = tableUtils.getTable(target, columns, null, null);
            if (CollectionUtils.isEmpty(events)) {
                System.out.println(" null");
            } else {
                classFindInterface.process(events);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (transport != null)
                    transport.close();
                if (snmp != null)
                    snmp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static OID[] buildOids(String[] oids) {
        OID[] columns = new OID[oids.length];
        for (int i = 0; i < oids.length; i++)
            columns[i] = new OID(oids[i]);
        return columns;
    }

    private static TableUtils buildTableUtils(Snmp snmp) {
        return new TableUtils(snmp, new PDUFactory() {
            @Override
            public PDU createPDU(MessageProcessingModel messageProcessingModel) {
                return null;
            }

            @Override
            public PDU createPDU(Target arg0) {
                PDU request = new PDU();
                request.setType(PDU.GET);
                return request;
            }
        });
    }

    private static CommunityTarget buildCommunityTarget() {
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setRetries(2);
        target.setAddress(GenericAddress.parse("udp:" + ip + "/161"));
        target.setTimeout(8000);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }
}
