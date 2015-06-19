package api.coupon;

public class Coupon {

    private int id;

    private String nom;

    private String reduction;

    public Coupon() {
    }

    public Coupon(int id, String nom, String reduction) {
        this.id = id;
        this.nom = nom;
        this.reduction = reduction;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getReduction() {
        return reduction;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setReduction(String reduction) {
        this.reduction = reduction;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", reduction='" + reduction + '\'' +
                '}';
    }
}
