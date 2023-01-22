package com.example.tms_app.AppUi

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.tms_app.R
import com.example.tms_app.ui.theme.Blue
import com.example.tms_app.ui.theme.StarColor
import com.example.tms_app.ui.theme.suspendy
import kotlin.reflect.jvm.internal.impl.protobuf.LazyStringList


@Composable
fun previewDetails(
    post_uri: String,
    title: String,
    releaseDate: String,
    rate: String,
    duration: String,
    overView: String,
    status: String,
    balance: String,
    voteCount: String
){
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .weight(1f)
                .padding(0.dp),
        ) {
                Image(painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500/$post_uri"),null,
                    contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                )

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .weight(1f)
                .padding(8.dp),
        )
        {
            Column() {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.robotomedium, FontWeight.Medium)),
                        fontSize = 38.sp,
                        color = Color.Black
                    )
                )
                Row() {
                    Text(
                        text = releaseDate ,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.robotothinitalic, FontWeight.SemiBold)),
                            fontSize = 22.sp,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Row() {
                        Icon(painter = painterResource(id = R.drawable.ic_baseline_star_rate_24), contentDescription = null, tint = StarColor )
                        Text(
                            text = rate,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.robotothinitalic, FontWeight.SemiBold)),
                                fontSize = 22.sp,
                                color = Color.Black
                            )
                        )

                    }
                    Spacer(modifier = Modifier.width(9.dp))
                    Row{
                        Icon(painter = painterResource(id = R.drawable.duration), contentDescription = null, tint = suspendy, modifier = Modifier.size(20.dp) )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = duration,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.robotothinitalic, FontWeight.SemiBold)),
                                fontSize = 22.sp,
                                color = Color.Black
                            )
                        )
                    }
                }
                Column() {
                    Text(
                        text = "overView",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.robotoregular, FontWeight.ExtraBold)),
                            fontSize = 34.sp,
                            color = Color.Black
                        )
                    )
                    Text(
                        text = overView,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.robotoregular, FontWeight.Bold)),
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Column() {
                    Text(
                        text = "- status: $status",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.robotoregular, FontWeight.ExtraBold)),
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    )
                    Text(
                        text = "- balance: $balance$",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.robotoregular, FontWeight.ExtraBold)),
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    )

                }
            }
        }
    }
}

