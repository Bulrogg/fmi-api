package api.coupon;

public class Coupon {

    private Integer id;

    private String nom;

    private String reduction;

    private Boolean estUtilise;

    public Coupon() {
    }

    public Coupon(String nom, String reduction, Boolean estUtilise) {
        this.nom = nom;
        this.reduction = reduction;
        this.estUtilise = estUtilise;
    }

    public Coupon(Integer id, String nom, String reduction, Boolean estUtilise) {
        this.id = id;
        this.nom = nom;
        this.reduction = reduction;
        this.estUtilise = estUtilise;
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

    public Boolean getEstUtilise() {
        return estUtilise;
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

    public void setEstUtilise(Boolean estUtilise) {
        this.estUtilise = estUtilise;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", reduction='" + reduction + '\'' +
                ", estUtilise=" + estUtilise +
                '}';
    }
}
