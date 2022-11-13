package com.jo.kisapi.composetest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jo.kisapi.R
import com.jo.kisapi.activity.InquireBalanceActivity
import com.jo.kisapi.ui.theme.KISapiTheme
import com.jo.kisapi.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inquireBalanceActivityIntent = Intent(this, InquireBalanceActivity::class.java)
        val autoTrading1Intent = Intent(this, AutoTrading1::class.java)
        val autoTrading2Intent = Intent(this, AutoTrading2::class.java)

        setContent() {
            KISapiTheme {
                Column(
                    Modifier
                        .padding(all = 10.dp)
                        .fillMaxWidth()
                ) {
                    Top() { startActivity(inquireBalanceActivityIntent) }
                    TradingView(name = "변동성 돌파 전략", -445){ startActivity(autoTrading1Intent) }
                    TradingView(name = "일중 모멘텀 전략", 370) { startActivity(autoTrading2Intent) }
                    TradingView(name = "준비중", 0) {}
                }
            }
        }
    }

}

@Composable
fun Top(onClick: () -> Unit) {
    KISapiTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            Arrangement.SpaceBetween
        ) {
            Text(
                fontSize = 30.sp,
                text = "AutoTrading",
            )
            Image(
                modifier = Modifier.clickable(onClick = onClick),
                painter = painterResource(id = R.drawable.ic_baseline_account_balance_wallet_24),
                contentDescription = ""
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TradingView(name: String, amount: Int, onClick: () -> Unit) {
    KISapiTheme {
        Card(

            shape = RoundedCornerShape(15.dp),
            elevation = 10.dp,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(150.dp)
                .clickable( onClick = onClick),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(
                    text = name,
                    fontSize = 30.sp,
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = if (amount == 0) "" else "누적손익 : ")
                Text(
                    text = if (amount == 0) "" else amount.toString(),
                    color = if (amount > 0) Color(0xFFFF0000)
                    else if (amount < 0) Color(0xFF0000FF)
                    else Color(0xFF000000)
                )

            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    KISapiTheme() {
        Column(
            Modifier
                .padding(all = 10.dp)
                .fillMaxWidth()
        ) {
            Top() {}
            TradingView(name = "변동성 돌파 전략", -445) { Log.d("test", "Test") }
            TradingView(name = "일중 모멘텀 전략", 370) { Log.d("test", "Test") }
            TradingView(name = "준비중", 0) { Log.d("test", "Test") }

        }
    }
}

