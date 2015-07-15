package api.services;

public class BaseMockController {

    protected void simulerUneLatenceEntre200Et(int nbMaxMs) {
        int tpsLatence = 200 + ((int) (Math.random() * (nbMaxMs - 200)));
        try {
            Thread.sleep(tpsLatence);
        } catch (InterruptedException e) {
        }
    }

    protected void log(String str) {
        System.out.println("----------------> " + str);
    }

}
