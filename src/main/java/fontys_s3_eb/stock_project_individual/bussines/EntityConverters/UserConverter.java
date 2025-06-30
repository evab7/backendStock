package fontys_s3_eb.stock_project_individual.bussines.EntityConverters;


import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;

import fontys_s3_eb.stock_project_individual.domain.UserPackage.User;


public final class UserConverter {

    public static User convert(UserEntity user)
    {
        return User.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
    public static UserEntity convertToEntity(User user)
    {
        return UserEntity.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
