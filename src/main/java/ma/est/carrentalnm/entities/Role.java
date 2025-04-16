package ma.est.carrentalnm.entities;

import jakarta.persistence.*;

import java.util.Collection;

    @Entity
    public class Role {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String name;
        @ManyToMany(mappedBy = "roles")
        private Collection<User> users;

        public Role(String name){
            this.name=name;
        }

        public Role() {

        }

        public String getName() {
            return name;
        }
    }

