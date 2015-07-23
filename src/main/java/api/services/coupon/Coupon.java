package api.services.coupon;

import api.data.CouponData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="Coupon", description="Model de coupon échangé pendant les appels")
public class Coupon {

    @ApiModelProperty(value = "Identifiant unique du coupon. Null si le coupon n'a pas été posté", notes = "null si le coupon n'a pas été encore posté", position = 0)
    private Integer id;

    @ApiModelProperty(value = "Nom du coupon", position = 1, required = true)
    private String nom;

    @ApiModelProperty(value = "Réduction du coupon", position = 2, required = true)
    private String reduction;

    @ApiModelProperty(value = "True si le coupon a été utilisé et false sinon", position = 3)
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

    public Coupon(CouponData couponData) {
        this.id = couponData.getId();
        this.nom = couponData.getNom();
        this.reduction = couponData.getReduction();
        this.estUtilise = couponData.getEstUtilise();
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
