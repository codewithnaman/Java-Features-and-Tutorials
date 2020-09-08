package java9.feature10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static java9.feature10.CurrencyConverter.sleep;

public class TimeOutExample {
    private static CurrencyConverter converter = new CurrencyConverter();

    public static void main(String[] args) throws InterruptedException {
        //java8CompletableFuture();
        java9CompletableFuture();
        sleep(5000);
    }

    private static void java9CompletableFuture() {
        CompletableFuture<Double> completeTimeout =
                CompletableFuture.supplyAsync(()-> converter.convertCurrency("USD","INR"));
        completeTimeout.thenAccept(System.out::println);
        completeTimeout.completeOnTimeout(50.0,2, TimeUnit.SECONDS);

        CompletableFuture<Double>  orTimeout=
                CompletableFuture.supplyAsync(()-> converter.convertCurrency("USD","INR"));
        orTimeout.exceptionally(TimeOutExample::reportError).thenAccept(System.out::println);
        orTimeout.orTimeout(2, TimeUnit.SECONDS);

        CompletableFuture<Double> converterFutureJpy =
                CompletableFuture.supplyAsync(()-> converter.convertCurrency("JPY","INR"));
        converterFutureJpy.exceptionally(TimeOutExample::reportError).thenAccept(System.out::println);
    }

    private static void java8CompletableFuture() {
        CompletableFuture<Double> converterFuture =
                CompletableFuture.supplyAsync(()-> converter.convertCurrency("USD","INR"));
        converterFuture.thenAccept(System.out::println);

        CompletableFuture<Double> converterFutureJpy =
                CompletableFuture.supplyAsync(()-> converter.convertCurrency("JPY","INR"));
        converterFutureJpy.exceptionally(TimeOutExample::reportError).thenAccept(System.out::println);
    }

    public static double reportError(Throwable throwable){
        System.out.println(throwable);
        throw new RuntimeException(throwable.getMessage());
    }
}
