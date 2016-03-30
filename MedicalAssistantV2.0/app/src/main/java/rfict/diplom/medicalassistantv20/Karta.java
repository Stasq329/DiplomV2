package rfict.diplom.medicalassistantv20;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;

public class Karta extends AppCompatActivity {
    public static enum TransitionType {SlideLeft}
    public static TransitionType transitionType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karta);
    }
}

