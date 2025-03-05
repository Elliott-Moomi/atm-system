package za.co.nesloedufy.codepotatoes.atm;

import za.co.nesloedufy.codepotatoes.atm.enums.CardStatus;
import za.co.nesloedufy.codepotatoes.atm.pojos.Card;

import java.time.LocalDate;
import java.util.Random;

public class CardManagement {


    private static Random random = new Random();

    public CardManagement(){

    }

    public static Card generateCard(int pin, String bankName){
        long cardNumber = random.nextLong(9999_9999_9999_9999L)+1000_0000_0000_0000L;
        long accountNumber = random.nextLong(99999_99999L)+10000_00000L;
        int cvv = random.nextInt(999)+100;
        LocalDate today = LocalDate.now();
        LocalDate expiryDate = today.plusYears(7);
        Card card = new Card(bankName,cardNumber,accountNumber,cvv,pin, CardStatus.UNFROZEN,expiryDate);

        return card;
    }
}
