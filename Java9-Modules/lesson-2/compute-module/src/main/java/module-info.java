module com.example.computation {
    exports com.example.compute;
    exports com.example.random;
    opens com.example.random.impl to com.example.user.application;
}