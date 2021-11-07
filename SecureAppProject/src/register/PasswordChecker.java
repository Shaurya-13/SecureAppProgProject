package register;

import java.text.DecimalFormat;

public class PasswordChecker {

    private String password;
    private String confirmPassword;
    private int pin;
    private String message;
    private Boolean valid;

    public PasswordChecker(String password, String confirmPassword, String pin) {

        this.password = password;
        this.confirmPassword = confirmPassword;
        this.valid = false;
        this.pin = Integer.parseInt(pin);
        this.message = "";

    }


    public boolean isValidPassword(){
     return this.message.equals("OK");
    }

    public String validatePasswordPin(){
        boolean passwordMatch = this.password.equals(this.confirmPassword);
        boolean pinLength = (int) (Math.log10(this.pin)+1) == 4;
        boolean isCorrectLength     = false;
        boolean hasCapitalLetter    = false;
        boolean hasLowerLetter      = false;
        boolean hasNumber           = false;
        boolean hasSpecialCharacter = false;
        if(password.length()>=6){
            isCorrectLength=true;
        }
        for(int i=0;i<password.length();i++){
            char ch;
            ch=password.charAt(i);
            String specialCharacterString="!~@#$%^&*_-+=|(){}[]:;<>,.?";
            if(Character.isDigit(ch)){
                hasNumber=true;
            }
            else if(Character.isUpperCase(ch)){
                hasCapitalLetter=true;
            }
            else if(Character.isLowerCase(ch)){
                hasLowerLetter=true;
            }
            else if(specialCharacterString.contains(Character.toString(ch))){
                hasSpecialCharacter=true;
            }
        }
        if(!passwordMatch){
            this.message = "Passwords do not match";
        }else if(!pinLength){
            this.message = "Pin should contain four numbers";
        }else if(!isCorrectLength){
            this.message = "Password Should be at least 6 characters";
        }else if(!hasCapitalLetter){
            this.message = "Password Should contain a Capital Letter";
        }else if(!hasLowerLetter){
            this.message = "Password Should contain a Lower Case Letter";
        }else if(!hasNumber){
            this.message = "Password Should contain a Number";
        }else if(!hasSpecialCharacter){
            this.message = "Password Should contain a Special Character";
        }else{
            this.message = "OK";
        }
      return this.message;

    }

}
