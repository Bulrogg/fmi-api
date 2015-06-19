package api.coupon;

public class Coupon {

    private Integer id;

    private String nom;

    private String reduction;

    public Coupon() {
    }

    public Coupon(String nom, String reduction) {
        this.nom = nom;
        this.reduction = reduction;
    }

    public Coupon(Integer id, String nom, String reduction) {
        this.id = id;
        this.nom = nom;
        this.reduction = reduction;
    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getReduction() {
        return reduction;
    }

    public void setId(Integer id) {
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
