package api.data;

public class CouponData {

    private Integer id;

    private String nom;

    private String reduction;

    private Boolean estUtilise;

    private String unAutreChampsPurBack;

    public CouponData() {
    }

    public CouponData(Integer id, String nom, String reduction, Boolean estUtilise, String unAutreChampsPurBack) {
        this.id = id;
        this.nom = nom;
        this.reduction = reduction;
        this.estUtilise = estUtilise;
        this.unAutreChampsPurBack = unAutreChampsPurBack;
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

    public String getUnAutreChampsPurBack() {
        return unAutreChampsPurBack;
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

    public void setUnAutreChampsPurBack(String unAutreChampsPurBack) {
        this.unAutreChampsPurBack = unAutreChampsPurBack;
    }

    @Override
    public String toString() {
        return "CouponData{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", reduction='" + reduction + '\'' +
                ", estUtilise=" + estUtilise +
                ", unAutreChampsPurBack='" + unAutreChampsPurBack + '\'' +
                '}';
    }
}
