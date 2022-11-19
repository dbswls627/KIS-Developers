package com.jo.kisapi.composetest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jo.kisapi.viewmodel.AutoTrading1ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@AndroidEntryPoint
class AutoTrading1 : ComponentActivity() {

    private val viewModel: AutoTrading1ViewModel by viewModels()

    var longPosition: String = "069500"
    var shortPosition: String = "114800"

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getCash()
        viewModel.getLongTargetPrice(longPosition)
        viewModel.getShortTargetPrice(shortPosition)
        viewModel.getLongCurrentPrice(longPosition)
        viewModel.getShortCurrentPrice(shortPosition)

        setContent {
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
                                "현재가 : ",
                                viewModel.longCurrentPrice.value,
                                viewModel.longYDPrice.value
                            )
                            CardInfo(
                                "타겟가 : ",
                                viewModel.longTargetPrice.value,
                                viewModel.longYDPrice.value
                            )
                            count(viewModel.longCount,
                                viewModel.auto.value,
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
                                "현재가 : ",
                                viewModel.shortCurrentPrice.value,
                                viewModel.shortYDPrice.value
                            )
                            CardInfo(
                                "타겟가 : ",
                                viewModel.shortTargetPrice.value,
                                viewModel.shortYDPrice.value
                            )
                            count(viewModel.shortCount,
                                viewModel.auto.value,
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
                            try {
                                if ((viewModel.longMax.value * viewModel.longCount.value.toInt() <= viewModel.cashes.value) &&
                                    (viewModel.shortMax.value * viewModel.shortCount.value.toInt() <= viewModel.cashes.value)) {
                                    //주문 시작
                                    if (!viewModel.auto.value) {

                                        viewModel.auto.value = true
                                        //setEnable(false)
                                        viewModel.longAuto(longPosition)
                                        viewModel.shortAuto(shortPosition)

                                        //주문 취소
                                    } else {
                                        viewModel.auto.value = false
                                        //setEnable(true)

                                    }
                                } else {
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar("보유금액이 부족합니다.")
                                    }
                                    //Toast.makeText(this, "보유금액이 부족합니다", Toast.LENGTH_SHORT).show()
                                }
                                keyboardController?.hide()
                            } catch (e: Exception) {
                            }
                        },
                        Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = if (!viewModel.auto.value) Color.Green else Color.Red)
                    ) {
                        Text(text = if (!viewModel.auto.value) "주문" else "취소")
                    }
                }
            }

        }
    }
}


//종목명
@Composable()
fun StockName(name: String) {
    Text(
        fontSize = 20.sp,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        text = name
    )
}

//주문 개수
@Composable()
fun count(count: MutableState<String>, auto: Boolean, plus: () -> Unit, minus: () -> Unit) {
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(),
    ) {
        Row(
            Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.clickable(enabled = !auto, onClick = { minus() }),
                color = Color.Green,
                fontSize = 30.sp,
                text = " - "
            )
            BasicTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                modifier = Modifier.width(20.dp),
                maxLines = 1,
                value = count.value,
                onValueChange = {
                    if (3 >= it.length)
                        count.value = it.replace("([-|.|\n])".toRegex(), "")
                    if (it == "0") count.value = "1"
                },
            )
            Text(
                modifier = Modifier.clickable(enabled = !auto, onClick = { plus() }),
                color = Color.Green,
                fontSize = 30.sp,
                text = " + "
            )
        }
    }
}

//가격 정보
@Composable()
fun CardInfo(name: String, amount: Int, YDPrice: Int) {
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(),
    ) {
        Row(
            Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                fontSize = 20.sp,
                text = name,
                textAlign = TextAlign.Left
            )
            Text(
                fontSize = 20.sp,
                text = DecimalFormat("#,###").format((amount)),
                color = if (amount - YDPrice > 0) {
                    Color(0XFFFF0000)
                } else {
                    Color(0XFF0000FF)
                }
            )
            Column() {
                Text(
                    fontSize = 15.sp,
                    text = if (amount - YDPrice > 0) {
                        DecimalFormat("#,###").format((amount - YDPrice))
                    } else {
                        DecimalFormat("#,###").format((amount - YDPrice))

                    },
                    color = if (amount - YDPrice > 0) {
                        Color(0XFFFF0000)
                    } else {
                        Color(0XFF0000FF)
                    },
                    textAlign = TextAlign.Left
                )
                Text(
                    fontSize = 15.sp,
                    text = DecimalFormat("##.##").format(((amount - YDPrice) * 100 / YDPrice.toDouble())) + "%",
                    color = if (amount - YDPrice > 0) {
                        Color(0XFFFF0000)
                    } else {
                        Color(0XFF0000FF)
                    }
                )
            }
        }
    }

}

//주문 금액
@Composable
fun StockAmount(count: MutableState<String>, amount: Int) {
    Row(
        Modifier
            .padding(10.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 15.sp,
            text = if (count.value != "") {
                " 주문금액 ≒ ${DecimalFormat("#,###").format((count.value.toInt() * amount))}"
            } else {
                "주문금액 ≒ 0 "
            }
        )


    }

}

@Preview()
@Composable()
fun test() {
    Button(
        onClick = { /*TODO*/ },
        Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
    ) {
        Text(text = "주문")
    }
}

