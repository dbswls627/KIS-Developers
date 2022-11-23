package com.jo.kisapi.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jo.kisapi.dataModel.output1
import com.jo.kisapi.ui.theme.KISapiTheme
import com.jo.kisapi.viewmodel.InquireBalanceViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat


@AndroidEntryPoint
class InquireBalanceActivity : ComponentActivity() {

    private val viewModel: InquireBalanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getCash()
        viewModel.getInquireBalance()

        setContent {

            KISapiTheme {
                Column() {
                    Balance(viewModel)
                    MyLazyColumn(viewModel.output1List)
                }

            }
        }
    }
}

@Composable
fun Balance(viewModel: InquireBalanceViewModel) {
        Column(
            Modifier
                .background(color = Color(0xFFF1F1F1))
                .fillMaxWidth()
        ) {
            Spacer(Modifier.size(20.dp))
            Text(text = "총 잔액", Modifier.padding(horizontal = 20.dp))
            Text(text = DecimalFormat("#,###원").format(viewModel.sumAmt.collectAsState().value), Modifier.padding(horizontal = 20.dp))
            Card(
                shape = RoundedCornerShape(15.dp),
                elevation = 10.dp,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Column(Modifier.padding(10.dp)) {
                    Text(text = "주문가능 : ${viewModel.cashes.value}")
                    Text(text = "손익 : ${viewModel.sumEvluPflsAmt.collectAsState().value}")
                    Text(text = "수익률 : ${viewModel.rt.collectAsState().value}")
                    Text(text = "매입 금액 : ${viewModel.sumPchsAmt.collectAsState().value}")
                    Text(text = "평가 금액 : ${viewModel.sumEvluAmt.collectAsState().value}")
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

            Divider(
                color = Color.Black,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )

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
         //   Balance()
            MyLazyColumn(list)
        }

    }
}