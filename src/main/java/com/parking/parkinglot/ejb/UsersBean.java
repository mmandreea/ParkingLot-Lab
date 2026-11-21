package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UsersBean {
    private static final Logger LOG = Logger.getLogger(UsersBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;

    public List<UserDto> findAllUsers(){

        LOG.info("findAllUsers");
        try{

            TypedQuery<User> typedQuery= entityManager.createQuery("select u from User u", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUsersToDto(users);

        }catch(Exception e){
            throw new EJBException(e.getMessage());
        }

    }
    private List<UserDto> copyUsersToDto(List<User> users){
        List<UserDto> dtos = new ArrayList<>();
        for(User user : users){

            Long id=user.getId();
            String email=user.getEmail();
            String username=user.getUsername();
            dtos.add(new UserDto(id,email, username));

        }
        return dtos;
    }

}
