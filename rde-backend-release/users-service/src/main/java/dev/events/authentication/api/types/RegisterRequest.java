package dev.events.authentication.api.types;


import java.sql.Date;

public record RegisterRequest(
        String name,
        String email,
        String password,
        Date birth,
        //String campus_id,
        String student_id,
        String phone,
        String address,
        int scholarship
) {
}

