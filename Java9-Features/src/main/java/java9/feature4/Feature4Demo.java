package java9.feature4;

import java.lang.ref.Cleaner;
import java.lang.ref.PhantomReference;

class Resource implements AutoCloseable, Runnable {
    public static int calledFinalized;
    public static int calledClose;
    public static int calledCleanerRun;
    private int value;

    public Resource(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

/*    @Override
    protected void finalize() throws Throwable {
        calledFinalized++;
        System.out.println("Cleaned Resource");
    }*/

    @Override
    public void close() throws Exception {
        calledClose++;
        System.out.println("Resource Closed");
    }

    @Override
    public void run() {
        calledCleanerRun++;
        System.out.println("Cleaner Run Called ");
    }
}


class Holder implements AutoCloseable {

    private Resource resource;
    private Cleaner.Cleanable cleanable;
    private Cleaner cleaner = Cleaner.create();

    public Holder(int value) {
        resource = new Resource(value);
        cleanable = cleaner.register(this, resource);
    }

    public int performOperation() {
        return resource.getValue();
    }

    @Override
    public void close() throws Exception {
        //resource.close();
        cleanable.clean();
    }
}

public class Feature4Demo {

    public static void main(String[] args) {
   /*     for(int i=0;i<1000000;i++) {
            Holder holder = new Holder(9);
            holder = null;
        }
        System.out.println("Finalized Called : " + Resource.calledFinalized);*/
  /*      for(int i=0;i<1000000;i++) {
            try (Holder holder = new Holder(i)) {
                System.out.println(holder.performOperation());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Close method called : "+ Resource.calledClose);*/
     /*   try(Holder holder = new Holder(9)){
            System.out.println("Value from Holder :"+holder.performOperation());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
      /*  Holder holder = new Holder(9);
        new PhantomReference<Holder>(holder, null);
        System.out.println("Cleaner Called : " + Resource.calledCleanerRun);*/
        for(int i=0;i<20000;i++) {
            Holder holder = new Holder(9);
            new PhantomReference<Holder>(holder, null);
        }
        System.out.println("Cleaner Called : " + Resource.calledCleanerRun);
    }
}





