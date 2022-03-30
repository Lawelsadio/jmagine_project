package jmagine

import grails.gorm.transactions.Transactional

//import javax.mail.internet.AddressException
//import javax.mail.internet.InternetAddress

@Transactional
class UtilsService {

    def mailService

    def isValidPassword(String password) {
        // TODO - Implémenter une verification de la complexité de mot de passe
        if( password && ( password.size() > 5 ) ) return true
        else return false
    }

    def isEmailValid(String email) {
        return isValidEmailAddress(email)
    }

//   public static boolean isValidEmailAddress(String email) {
//        boolean result = true;
//        try {
//            InternetAddress emailAddr = new InternetAddress(email);
//            emailAddr.validate();
//        } catch (AddressException ex) {
//            result = false;
//        }
//        return result;
//    }
}
