package com.jo.kisapi.composetest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jo.kisapi.AlarmReceiver
import com.jo.kisapi.ui.theme.KISapiTheme
import com.jo.kisapi.viewmodel.AutoTradingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.*

@AndroidEntryPoint
class AutoTrading2 : ComponentActivity() {

    private val viewModel: AutoTradingViewModel by viewModels()

    var longPosition: String = "069500"
    var shortPosition: String = "114800"

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, AlarmReceiver::class.java)
        viewModel.getCash()

        viewModel.getLongTimePrice(longPosition)
        viewModel.getLongStarPrice(longPosition)
        viewModel.getLongMaxPrice(longPosition)

        viewModel.getShortTimePrice(shortPosition)
        viewModel.getShortStarPrice(shortPosition)
        viewModel.getShortMaxPrice(shortPosition)

        setContent {
            val context = LocalContext.current

            var pdno = ""
            var count = ""
            var amt = ""

            val scaffoldState = rememberScaffoldState()
            val scope = rememberCoroutineScope()
            val keyboardController = LocalSoftwareKeyboardController.current
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(title = {
                        Text("변동성 돌파 전략")
                    },
                        backgroundColor = Color(0xFFFFFFFF),
                        actions = {
                        })
                }) {
                Column(Modifier.padding(5.dp)) {
                    Row(Modifier.height(IntrinsicSize.Min)) {
                        Column(
                            Modifier
                                .weight(1f)
                                .padding(10.dp)
                        ) {
                            StockName("KODEX 200")
                            CardInfo(
                                "9:00 : ",
                                viewModel.longStartPrice.value,
                                viewModel.longYDPrice.value
                            )
                            CardInfo(
                                "9:30 : ",
                                viewModel.longTimePrice.value,
                                viewModel.longYDPrice.value
                            )
                            count(viewModel.longCount,
                                false,
                                { viewModel.longCountPlus() },
                                { viewModel.longCountMinus() })
                            StockAmount(viewModel.longCount, viewModel.longMax.value)

                        }
                        Divider(
                            color = Color.Black,
                            modifier = Modifier
                                .width(1.dp)
                                .fillMaxHeight()
                        )
                        Column(
                            Modifier
                                .weight(1f)
                                .padding(10.dp)
                        ) {
                            StockName("KODEX 인버스")
                            CardInfo(
                                "9:00 : ",
                                viewModel.shortStartPrice.value,
                                viewModel.shortYDPrice.value
                            )
                            CardInfo(
                                "9:30 : ",
                                viewModel.shortTimePrice.value,
                                viewModel.shortYDPrice.value
                            )
                            count(viewModel.shortCount,
                                false,
                                { viewModel.shortCountPlus() },
                                { viewModel.shortCountMinus() })
                            StockAmount(viewModel.shortCount, viewModel.shortMax.value)

                        }
                    }
                    Divider(
                        color = Color.Black, modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "주문 가능 금액 : ${DecimalFormat("#,###").format(viewModel.cashes.value)}"
                    )
                    Button(
                        onClick = {
                            if (viewModel.longStartPrice.value < viewModel.longTimePrice.value) {
                                pdno = "069500"
                                count = viewModel.longCount.value
                                amt = viewModel.longMax.value.toString()

                            } else if (viewModel.shortStartPrice.value < viewModel.shortTimePrice.value) {
                                pdno = "114800"
                                count = viewModel.shortCount.value
                                amt = viewModel.shortMax.value.toString()

                            }
                            if (pdno != "" && (viewModel.longMax.value * viewModel.longCount.value.toInt() <= viewModel.cashes.value) &&
                                (viewModel.shortMax.value * viewModel.shortCount.value.toInt() <= viewModel.cashes.value)
                            ) {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("주문전송 완료.")
                                }
                                //Toast.makeText(this, "주문전송 완료", Toast.LENGTH_SHORT).show()
                                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager


                                intent.putExtra("pdno", pdno)
                                intent.putExtra("amt", amt)
                                intent.putExtra("count", count)


                                val pendingIntent = PendingIntent.getBroadcast(
                                    context, 0, intent,
                                    PendingIntent.FLAG_IMMUTABLE
                                )

                                val calendar: Calendar = Calendar.getInstance().apply {
                                    timeInMillis = System.currentTimeMillis()
                                    set(Calendar.HOUR_OF_DAY, 15)
                                    set(Calendar.MINUTE, 0)
                                }

                                alarmManager.set(
                                    AlarmManager.RTC_WAKEUP,
                                    calendar.timeInMillis,
                                    pendingIntent
                                )
                            } else {
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar("보유금액이 부족합니다.")
                                }
                                //Toast.makeText(this, "보유금액이 부족합니다", Toast.LENGTH_SHORT).show()
                            }
                            keyboardController?.hide()
                        },
                        Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                    ) {
                        Text(text = "주문")
                    }
                }
            }

        }
    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    KISapiTheme {
        Greeting2("Android")
    }
}
