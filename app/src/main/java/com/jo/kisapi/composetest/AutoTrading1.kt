package com.jo.kisapi.composetest

import android.os.Bundle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jo.kisapi.viewmodel.AutoTrading1ViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class AutoTrading1 : ComponentActivity() {

    private val viewModel: AutoTrading1ViewModel by viewModels()

    var longPosition: String = "069500"
    var shortPosition: String = "114800"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getCash()
        viewModel.getLongTargetPrice(longPosition)
        viewModel.getShortTargetPrice(shortPosition)
        viewModel.getLongCurrentPrice(longPosition)
        viewModel.getShortCurrentPrice(shortPosition)

        setContent {
            Scaffold(
                scaffoldState = rememberScaffoldState(),
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
                            count(viewModel.longCount,{viewModel.longCountPlus()},{viewModel.longCountMinus()})
                            StockAmount()
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
                            count(viewModel.shortCount,{viewModel.shortCountPlus()},{viewModel.shortCountMinus()})
                            StockAmount()

                        }
                    }
                    Divider(
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "주문 가능 금액: ${viewModel.cashes.value.toString()}"
                    )
                    Button(onClick = { /*TODO*/ }, Modifier.fillMaxWidth()) {
                        Text(text = "주문")
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
fun count(count: MutableState<String>, plus: () -> Unit, minus: () -> Unit) {
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
                modifier = Modifier.clickable {
                    minus()
                },
                color = Color(0xFF2DB532),
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
                    if (it == "0") count.value = " 1"
                },
            )
            Text(
                modifier = Modifier.clickable {
                    plus()
                },
                color = Color(0xFF2DB532),
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
                        DecimalFormat("+#,###").format((amount - YDPrice))
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
                    text = DecimalFormat("+##.##").format(((amount - YDPrice) * 100 / YDPrice.toDouble())) + "%",
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
fun StockAmount() {
    Row(
        Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            fontSize = 15.sp,
            text = " 주문금액 = 200 "
        )


    }

}

@Preview()
@Composable()
fun test() {
    TextField(modifier = Modifier.width(10.dp), value = "", onValueChange = {})
}

