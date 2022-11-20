package com.jo.kisapi.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jo.kisapi.ui.theme.KISapiTheme

class InquireBalanceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting()
        }
    }
}

@Composable
fun Greeting() {
        Column(
            Modifier
                .background(color = Color.Gray)
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(text = "총 잔액", Modifier.padding(horizontal = 10.dp))
            Text(text = "20000", Modifier.padding(horizontal = 10.dp))
            Card(
                shape = RoundedCornerShape(15.dp),
                elevation = 10.dp,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Column(Modifier.padding(10.dp)) {
                    Text(text = "주문가능 : ")
                    Text(text = "손익 :")
                    Text(text = "수익률 ")
                    Text(text = "매입 금액 ")
                    Text(text = "평가 금액 ")
                }

            }
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Row(
                Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth(),
                Arrangement.SpaceEvenly
            ) {
                Column( horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "종목명")
                    Text(text = "")
                }
                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )
                Column( horizontalAlignment = Alignment.End) {
                    Text(text = "평가손익", )
                    Text(text = "평가수익률")
                }

                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )
                Column( horizontalAlignment = Alignment.End) {
                    Text(text = "잔고수량", )
                    Text(text = "")
                }
                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )
                Column( horizontalAlignment = Alignment.End) {
                    Text(text = "매입가", )
                    Text(text = "현재가", )
                }
                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )
                Column( horizontalAlignment = Alignment.End) {
                    Text(text = "매입금액", )
                    Text(text = "평가금액", )
                }

            }
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )


    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    KISapiTheme {
        Greeting()
    }
}