package com.jo.kisapi.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jo.kisapi.dataModel.output1
import com.jo.kisapi.ui.theme.KISapiTheme

class InquireBalanceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val list = listOf<output1>(
                output1("", "삼성", 1000, 1000f, 1000, 100, 100, 100, 100f),
                output1("", "삼성", 1000, 1000f, 1000, 100, 100, 100, 100f)
            )
            KISapiTheme {
                Column() {
                    Greeting()
                    MyLazyColumn(list)
                }

            }
        }
    }
}

@Composable
fun Greeting() {
        Column(
            Modifier
                .background(color = Color.Gray)
                .fillMaxWidth()
        ) {
            Spacer(Modifier.size(20.dp))
            Text(text = "총 잔액", Modifier.padding(horizontal = 20.dp))
            Text(text = "20000", Modifier.padding(horizontal = 20.dp))
            Card(
                shape = RoundedCornerShape(15.dp),
                elevation = 10.dp,
                modifier = Modifier
                    .padding(20.dp)
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
            DataType(s1 = "종목명", s2 = "평가손익", s3 = "잔고수량", s4 = "매입가", s5 = "매입금액")

            Divider(
                color = Color.Black,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            DataType(s1 = "", s2 = "평가수익률", s3 = "", s4 = "현재가", s5 = "평가금액")


        }
}

@Composable
fun DataType(s1: String, s2: String, s3: String, s4: String, s5: String) {
    Row(
        Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        Arrangement.SpaceEvenly
    ) {
        Text(text = s1, Modifier.weight(1f), textAlign = TextAlign.Center)

        Divider(
            color = Color.Black,
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Text(text = s2, Modifier.weight(1f), textAlign = TextAlign.End)

        Divider(
            color = Color.Black,
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Text(text = s3, Modifier.weight(1f), textAlign = TextAlign.End)

        Divider(
            color = Color.Black,
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Text(text = s4, Modifier.weight(1f), textAlign = TextAlign.End)

        Divider(
            color = Color.Black,
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )

        Text(text = s5, Modifier.weight(1f), textAlign = TextAlign.End)


    }
}
/*val pdno: String,
val prdt_name: String,
val hldg_qty: Int,           //보유수량
val pchs_avg_pric: Float,      //매입평균금액
val prpr: Int,               //현재가
val pchs_amt: Int,           //매입금액
val evlu_amt: Int,           //평가금액
val evlu_pfls_amt: Int,      //평가손익금액
val evlu_pfls_rt: Float        //평가손익률*/
@Composable
fun DataTop(output1: output1) {
    Row(
        Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        Arrangement.SpaceEvenly
    ) {
        Text(text = output1.prdt_name, Modifier.weight(1f), textAlign = TextAlign.Center)

        Divider(
            color = Color.Black,
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Text(text = output1.evlu_pfls_amt.toString(), Modifier.weight(1f), textAlign = TextAlign.End)

        Divider(
            color = Color.Black,
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Text(text = output1.hldg_qty.toString(), Modifier.weight(1f), textAlign = TextAlign.End)

        Divider(
            color = Color.Black,
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Text(text = output1.pchs_avg_pric.toString(), Modifier.weight(1f), textAlign = TextAlign.End)

        Divider(
            color = Color.Black,
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )

        Text(text = output1.pchs_amt.toString(), Modifier.weight(1f), textAlign = TextAlign.End)


    }
}

@Composable
fun DataBottom(output1: output1) {
    Row(
        Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        Arrangement.SpaceEvenly
    ) {
        Text(text = "", Modifier.weight(1f), textAlign = TextAlign.Center)

        Divider(
            color = Color.Black,
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Text(text = output1.evlu_pfls_rt.toString(), Modifier.weight(1f), textAlign = TextAlign.End)

        Divider(
            color = Color.Black,
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Text(text = "", Modifier.weight(1f), textAlign = TextAlign.End)

        Divider(
            color = Color.Black,
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Text(text = output1.prpr.toString(), Modifier.weight(1f), textAlign = TextAlign.End)

        Divider(
            color = Color.Black,
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )

        Text(text = output1.evlu_amt.toString(), Modifier.weight(1f), textAlign = TextAlign.End)


    }
}

@Composable
fun MyLazyColumn(list: List<output1>) {
    LazyColumn() {
        items(list){
            DataTop(it)
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            DataBottom(it)
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    val list = listOf<output1>(
        output1("", "삼성", 1000, 1000f, 1000, 100, 100, 100, 100f),
        output1("", "삼성", 1000, 1000f, 1000, 100, 100, 100, 100f)
    )
    KISapiTheme {
        Column() {
            Greeting()
            MyLazyColumn(list)
        }

    }
}