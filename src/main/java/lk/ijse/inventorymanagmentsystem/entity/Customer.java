package lk.ijse.inventorymanagmentsystem.entity;

import lk.ijse.inventorymanagmentsystem.dto.CustomerDTO;

public class Customer {



        private int id;
        private String name;
        private String contact;



        public Customer() {
        }

        public Customer(String name, String contact ) {
            this.name = name;
            this.contact = contact;

        }

        public Customer(int id, String name, String contact) {
            this.id = id;
            this.name = name;
            this.contact = contact;

        }

        public Customer(CustomerDTO customerDTO) {
            this.id = customerDTO.getId();
            this.name = customerDTO.getName();
            this.contact = customerDTO.getContact();
        }



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }




        @Override
        public String toString() {
            return "CustomerDTO{" + "id=" + id + ", name=" + name + ", contact=" + contact + '}';
        }


}
