package rain.redistransaction;

// redis 事务 解决超卖场景
public class redisUtil {

    public long decr(String key, long value) {
        // If any error, return -1.
//        if (value <= 0)
//            return -1;
//
//        // Start the CAS operations.
//        jedis.watch(key);
//
//        // Start the transation.
//        Transaction tx = jedis.multi();
//
//        // Decide if the left value is less than 0, if no, terminate the
//        // transation, return -1;
//        String curr = tx.get(key).get();
//        if (Long.parseLong(curr) - value < 0) {
//            tx.discard();
//            return -1;
//        }
//
//        // Minus the key by the value
//        tx.decrBy(key, value);
//
//        // Execute the transation and then handle the result
//        List<Object> result = tx.exec();
//
//        // If error, return -1;
//        if (result == null || result.isEmpty()) {
//            return -1;
//        }
//
//        // Extract the first result
//        for (Object rt : result) {
//            return Long.parseLong(rt.toString());
//        }

        // The program never comes here.
        return -1;
    }
}
