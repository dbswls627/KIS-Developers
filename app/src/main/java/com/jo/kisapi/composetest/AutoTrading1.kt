package com.jo.kisapi.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AutoTrading1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(topBar = {
                TopAppBar(title = {
                    Text("변동성 돌파 전략")
                },
                    backgroundColor = Color(0xFFFFFFFF),
                    actions = {
                    })
            }) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        Modifier
                            .padding(10.dp)
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        AutoTradingView1()
                    }
                    Column(
                        Modifier
                            .padding(10.dp)
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        AutoTradingView1()
                    }
                }

            }
        }
    }

}

@Composable
fun AutoTradingView1() {
    Text(
        fontSize = 20.sp,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        text = "KODEX 200 "
    )
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

            ) {
            Text(
                fontSize = 15.sp,
                text = "현재가 : ",
                textAlign = TextAlign.Left
            )
            Text(
                fontSize = 20.sp,
                text = "2000 "
            )
        }
    }
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(),
    ) {
        Row(
            Modifier.padding(10.dp),
        ) {
            Text(
                fontSize = 20.sp,
                text = "타겟가 : "
            )
            Text(
                fontSize = 20.sp,
                text = "2000 "
            )
        }

    }
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                color = Color(0xFF2DB532),
                fontSize = 20.sp,
                text = " - "
            )
            Text(
                fontSize = 15.sp,
                text = "2000 "
            )
            Text(
                color = Color(0xFF2DB532),
                fontSize = 20.sp,
                text = " + "
            )

        }

    }


}

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("변동성 돌파 전략")
        },
            backgroundColor = Color(0xFFFFFFFF),
            actions = {
            })
    }) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                AutoTradingView1()
            }
            Column(
                Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                AutoTradingView1()
            }
        }

    }
}