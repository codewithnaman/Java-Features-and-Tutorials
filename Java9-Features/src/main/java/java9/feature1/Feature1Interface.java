package java9.feature1;

public interface Feature1Interface {

    default int numbersOfThreadsAvailable(){
        return numberOfCoresSystemHave();
    }

    default long totalMemory(){
        return getRuntime().totalMemory()/(1024*1024);
    }

    private int numberOfCoresSystemHave(){
        return getRuntime().availableProcessors();
    }

    private static Runtime getRuntime(){
        return Runtime.getRuntime();
    }


}
