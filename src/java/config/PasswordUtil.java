/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 *
 * @author nkiem
 */
public class PasswordUtil {
    
    public String hashPassword(String plainPassword) {
        return BCrypt.withDefaults().hashToString(10, plainPassword.toCharArray());
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword).verified;
    }
}
