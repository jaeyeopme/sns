package me.jaeyeopme.sns.user.presentation.dto;

public record UserCreateRequest(String email,
                                String phone,
                                String password,
                                String name,
                                String bio) {

}
