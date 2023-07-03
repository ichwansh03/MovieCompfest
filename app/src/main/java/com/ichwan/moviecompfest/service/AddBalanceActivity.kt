package com.ichwan.moviecompfest.service

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ichwan.moviecompfest.auth.LoginActivity
import com.ichwan.moviecompfest.databinding.ActivityAddBalanceBinding

class AddBalanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBalanceBinding

    object Withdraw{
        var balances : Int = 0
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBalanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wd50.setOnClickListener { binding.etBalance.setText("50000") }
        binding.wd100.setOnClickListener { binding.etBalance.setText("100000") }
        binding.wd250.setOnClickListener { binding.etBalance.setText("250000") }
        binding.wd500.setOnClickListener { binding.etBalance.setText("500000") }

        insertWithdraw()
    }

    private fun insertWithdraw() {
        val url: String = GlobalData.BASE_URL+"balance/withdraw.php"
        val queue = Volley.newRequestQueue(this)
        val request = object : StringRequest(Method.POST, url, Response.Listener { _ ->
            Toast.makeText(this, "Balance succesfully added", Toast.LENGTH_SHORT).show()
            finish()
        },
        Response.ErrorListener {
            error ->
            Log.d("error manage balance ",error.message.toString())
        }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = LoginActivity.UserData.username
                params["balance"] = binding.etBalance.toString()
                Withdraw.balances = binding.etBalance.toString().toInt()
                return params
            }
        }
        queue.add(request)
    }
}