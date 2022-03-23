package restaurant.voting.system.modelo;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Restaurant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String address;
    private String website;
    private Integer votes;
    @Enumerated(EnumType.STRING)
    private StatusVote status = StatusVote.VALID;
    @Column(columnDefinition = "DATE")
    private final LocalDate day = LocalDate.now();

    public Restaurant() {
    }

    public Restaurant(String name, String mensagem) {
        this.name = name;
        this.description = mensagem;
    }

    public Integer addVote(Integer number){
        return this.getVotes() + number;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Restaurant other = (Restaurant) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusVote getStatus() {
        return status;
    }

    public void setStatus(StatusVote status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public LocalDate getDay() {
        return day;
    }
}


