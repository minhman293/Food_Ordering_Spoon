package fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.man293.food_ordering_spoon.R;

public class fragment_PRODUCT extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_product);
        button=(Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tạo 1 biến layoutInterflater
                LayoutInflater li = getLayoutInflater();
                //lấy đối tượng View được định nghĩa trong file customtoast.xml
                View layout = li.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.custom)); // id của LinearLayout trong custom_toast.xml


                TextView text =(TextView)layout.findViewById(R.id.text);

                //Creating the Toast object : 1 chế độ xem chứa thông báo nhỏ
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP |Gravity.FILL_HORIZONTAL,0,20); // chỉnh
                toast.setView(layout);
                toast.show();
            }
        });


    }
}