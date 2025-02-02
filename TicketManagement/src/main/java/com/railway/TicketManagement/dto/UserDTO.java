package com.railway.TicketManagement.dto;
import com.railway.TicketManagement.entities.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String password;

    // Constructor to convert User entity to UserDTO
    public UserDTO(User user) {
        this.userId = Long.valueOf(user.getUserId());
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.contactNumber = user.getContactNumber();
        this.password = user.getPassword();
    }

}

