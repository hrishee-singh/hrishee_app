package com.hrisheekesh.hrishee.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hrisheekesh.hrishee.R
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {


    private var paymentResultListener: Boolean = false
    lateinit var btnFremium: Button
    lateinit var btnGood: Button
    lateinit var btnBest: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFremium = findViewById(R.id.btnFremium)
        btnFremium.setOnClickListener {
            val intent = Intent(this, FremiumActivity::class.java)
            startActivity(intent)
        }

        btnGood = findViewById(R.id.btnGood)
        btnGood.setOnClickListener {
            makePayment()

        }

        btnBest = findViewById(R.id.btnBest)
        btnBest.setOnClickListener {
            makePaymentPro()

        }
    }

    private fun makePayment() {
        val co = Checkout()


        try {
            val options = JSONObject()
            options.put("name", "Hrishee")
            options.put("description", "Pay to get subscrition")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "5000")//pass amount in currency subunits


            val prefill = JSONObject()
            prefill.put("email", "")
            prefill.put("contact", "")

            options.put("prefill", prefill)
            co.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }

    }

    private fun makePaymentPro() {
        val co = Checkout()
        paymentResultListener = true

        try {
            val options = JSONObject()
            options.put("name", "Hrishee")
            options.put("description", "Pay to get subscrition")
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "8000")//pass amount in currency subunits


            val prefill = JSONObject()
            prefill.put("email", "")
            prefill.put("contact", "")

            options.put("prefill", prefill)
            co.open(this, options)


        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }


    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Sccessful: $p0", Toast.LENGTH_LONG).show()

        if (paymentResultListener) {
            val intent = Intent(this, BestActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, GoodActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Error: $p1", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        val intent = Intent(this, FremiumActivity::class.java)
        startActivity(intent)

        super.onBackPressed()
    }
}






