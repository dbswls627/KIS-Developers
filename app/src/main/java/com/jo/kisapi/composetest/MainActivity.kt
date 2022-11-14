package com.jo.kisapi.composetest

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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

        viewModel.getTokenCheck()
        viewModel.changeRefresh()

        val inquireBalanceActivityIntent = Intent(this, InquireBalanceActivity::class.java)
        val autoTrading1Intent = Intent(this, AutoTrading1::class.java)
        val autoTrading2Intent = Intent(this, AutoTrading2::class.java)

        setContent() {
            KISapiTheme {
                Scaffold(topBar = {
                    TopAppBar(title = {
                        Text("Auto Trading")
                    },
                        backgroundColor = Color(0xFFFFFFFF),
                        actions = {
                            Image(
                                modifier = Modifier
                                    .clickable {
                                        startActivity(
                                            inquireBalanceActivityIntent
                                        )
                                    }
                                    .padding(10.dp),

                                painter = painterResource(id = R.drawable.ic_baseline_account_balance_wallet_24),
                                contentDescription = ""
                            )
                        })
                }) {
                    Column(
                        Modifier
                            .padding(all = 10.dp)
                            .fillMaxWidth()
                    ) {
                        TradingView(name = "변동성 돌파 전략", viewModel.aChange.value) {
                            startActivity(
                                autoTrading1Intent
                            )
                        }
                        TradingView(name = "일중 모멘텀 전략", viewModel.bChange.value) {
                            startActivity(
                                autoTrading2Intent
                            )
                        }
                        TradingView(name = "준비중", 0) {}
                    }
                }

            }
        }
    }

}



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
                .clickable(onClick = onClick),
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
        KISapiTheme {
            Scaffold(topBar = {
                TopAppBar(title = {
                    Text("Auto Trading")
                },
                    backgroundColor = Color(0xFFFFFFFF),
                    actions = {
                        Image(
                            modifier = Modifier
                                .clickable {

                                }
                                .padding(10.dp),

                            painter = painterResource(id = R.drawable.ic_baseline_account_balance_wallet_24),
                            contentDescription = ""
                        )
                    })
            }) {
                Column(
                    Modifier
                        .padding(all = 10.dp)
                        .fillMaxWidth()
                ) {
                    TradingView(name = "변동성 돌파 전략", 100) {

                    }
                    TradingView(name = "일중 모멘텀 전략", -100) {

                    }
                    TradingView(name = "준비중", 0) {}
                }
            }

        }
    }

}

