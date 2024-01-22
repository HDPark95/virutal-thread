package my.study.virtualthread.threadlocal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ThreadLocalTest {

    @Test
    void 가상스레드_생성_테스트() {
        Thread.startVirtualThread(() -> {
            System.out.println("Hello Virtual Thread");
            System.out.println("Thread is Virtual? " + Thread.currentThread().isVirtual());
            Assertions.assertTrue(Thread.currentThread().isVirtual());
        });

        Runnable runnable = () -> {
            System.out.println("Hi Virtual Thread");
            System.out.println("Thread is Virtual? " + Thread.currentThread().isVirtual());
            Assertions.assertTrue(Thread.currentThread().isVirtual());
        };

        Thread virtualThread1 = Thread.ofVirtual().start(runnable);
        System.out.println("Thread is Virtual? " + virtualThread1.isVirtual());
        Assertions.assertTrue(virtualThread1.isVirtual());

        Thread.Builder builder = Thread.ofVirtual().name("JVM-Thread");
        Thread virtualThread2 = builder.start(runnable);
        System.out.println("Thread is Virtual? " + virtualThread2.isVirtual());

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i <3; i++) {
                executorService.submit(() -> {
                    runnable.run();
                    System.out.println("Thread is Virtual? " + Thread.currentThread().isVirtual());
                    Assertions.assertTrue(Thread.currentThread().isVirtual());
                });
            }
        }
    }
}
