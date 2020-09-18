package pl.mikel.insurance.dao;

import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "insurance_table")
@Indexed
public class InsuranceDao {

    private long id;

    @Override
    public String toString() {
        return "InsuranceDao{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", clientSurname='" + clientSurname + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carSubModel='" + carSubModel + '\'' +
                ", yearOfProduction=" + yearOfProduction +
                ", fuel='" + fuel + '\'' +
                ", capacity=" + capacity +
                ", doors=" + doors +
                ", mileage=" + mileage +
                ", usage='" + usage + '\'' +
                ", ageOfUser=" + ageOfUser +
                ", damage=" + damage +
                ", actualUser='" + actualUser + '\'' +
                ", price=" + price +
                '}';
    }


    private String clientName;
    private String clientSurname;
    private String carModel;
    private String carSubModel;
    private Integer yearOfProduction;
    private String fuel;
    private Double capacity;
    private Integer doors;
    private Integer mileage;
    private String usage;
    private Integer ageOfUser;
    private Integer damage;
    private String actualUser;
    private Double price;


    public InsuranceDao() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Field
    @Column(name = "fuel")
    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    @Column(name = "capacity")
    @Field
    @NotNull(message = "{pl.mikel.insurance.dao.InsuranceDao.capacity.NotNull}")
    @Min(value = 1, message = "{pl.mikel.insurance.dao.InsuranceDao.capacity.Min}")
    @Max(value = 10, message = "{pl.mikel.insurance.dao.InsuranceDao.capacity.Max}")
    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    @Column(name = "numberOfDoors")
    @Field
    @Min(value = 1)
    @NotNull(message = "{pl.mikel.insurance.dao.InsuranceDao.doors.NotNull}")
    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    @Column(name = "mileage")
    @Field
    @NotNull(message = "{pl.mikel.insurance.dao.InsuranceDao.mileage.NotNull}")
    @Min(value = 0, message = "{pl.mikel.insurance.dao.InsuranceDao.mileage.Min}")
    @Max(value = 999999, message = "{pl.mikel.insurance.dao.InsuranceDao.mileage.Max}")
    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    @Column(name = "usage")
    @Field
    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Column(name = "age")
    @Field
    @NotNull(message = "{pl.mikel.insurance.dao.InsuranceDao.ageOfUser.NotNull}")
    @Min(value = 18, message = "{pl.mikel.insurance.dao.InsuranceDao.ageOfUser.Min}")
    public Integer getAgeOfUser() {
        return ageOfUser;
    }

    public void setAgeOfUser(Integer ageOfUser) {
        this.ageOfUser = ageOfUser;
    }

    @Column(name = "damage")
    @Field
    @NotNull(message = "{pl.mikel.insurance.dao.InsuranceDao.damage.NotNull}")
    @Min(value = 0, message = "{pl.mikel.insurance.dao.InsuranceDao.damage.Min}")
    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }


    @Column(name = "actualUser")
    @Field
    public String getActualUser() {
        return actualUser;
    }

    public void setActualUser(String actualUser) {
        this.actualUser = actualUser;
    }

    @Column(name = "price")
    @Field
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "clientName")
    @Field
    @NotEmpty(message = "{pl.mikel.insurance.dao.InsuranceDao.clientName.NotEmpty}")
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Column(name = "clientSurname")
    @Field
    @NotEmpty(message = "{pl.mikel.insurance.dao.InsuranceDao.clientSurname.NotEmpty}")
    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    @Column(name = "carModel")
    @Field
    @NotEmpty(message = "{pl.mikel.insurance.dao.InsuranceDao.carModel.NotEmpty}")
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @Column(name = "carSubModel")
    @Field
    @NotEmpty(message = "{pl.mikel.insurance.dao.InsuranceDao.carSubModel.NotEmpty}")
    public String getCarSubModel() {
        return carSubModel;
    }

    public void setCarSubModel(String carSubModel) {
        this.carSubModel = carSubModel;
    }

    @Column(name = "yearOfProduction")
    @Field
    @NotNull(message = "{pl.mikel.insurance.dao.InsuranceDao.yearOfProduction.NotNull}")
    @Min(value = 1950, message = "{pl.mikel.insurance.dao.InsuranceDao.yearOfProduction.Min}")
    @Max(value = 2020, message = "{pl.mikel.insurance.dao.InsuranceDao.yearOfProduction.Max}")
    public Integer getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(Integer yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }


}
