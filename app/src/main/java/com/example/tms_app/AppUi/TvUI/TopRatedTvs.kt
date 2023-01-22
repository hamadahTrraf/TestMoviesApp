package com.example.tms_app.AppUi.TvUI

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.tms_app.DataBase.Movie
import com.example.tms_app.DataClasses.MovieInitialInfo
import com.example.tms_app.DataClasses.TvInitialInfo
import com.example.tms_app.R
import com.example.tms_app.Viewmodels.MainViewModel
import com.example.tms_app.Viewmodels.TvViewModel

@Composable
fun TopRatedTvs(viewModel: TvViewModel){
    LaunchedEffect(key1 = false){
        Log.e("hhhg","443")
        viewModel.emptyTvsFromDatyaBase()
        viewModel.getTopRatedTv(1)
    }
    val tvs by viewModel.tv.collectAsState()
    LazyColumn{
        /*items(movies){item->
            UserNameRow(item)
        }*/
        items(tvs){
            item->
            if(item != null){
                TvItemToShow(
                    TvInitialInfo(
                        id = item.id,
                        image = R.drawable.user,
                        name = item.original_title,
                        popularity = item.popularity.toString(),
                        isFavourite = item.isFavourite,
                        uri = if(item.poster_path != null) "https://image.tmdb.org/t/p/w500/${item.poster_path}" else null
                        )
                ){newState->
                    if(newState){
                        viewModel.setFavouriteOn(item.id,"tv")
                    }
                    else{
                        viewModel.setFavouriteOff(item.id,"tv")
                    }
                }
            }
        }
    }

}

@Composable
fun showSingleTvItem(moviePoster: String, title: String){
    Box(
        modifier = Modifier
            .width(75.dp)
            .height(75.dp)
            .clip(shape = RectangleShape)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .size(size = 55.dp)
                    .clip(shape = RectangleShape)
                    .height(55.dp)
                ,
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Profile Image"
            )
            Box(
                modifier = Modifier
                    .size(20.dp)
            ){
                Text(text = title )
            }
        }
    }
}







@Composable
fun TvItemToShow(
    item: TvInitialInfo,
    context: Context = LocalContext.current.applicationContext,
    onStarClick:(Boolean)->Unit
) {

    Box(
        modifier = Modifier
            .clickable(
                onClick = {
                    onStarClick(!item.isFavourite)
                }
            )
            .padding(vertical = 10.dp, horizontal = 5.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
                ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Profile image
                Image(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(56.dp),
                    painter = if (item.uri != null) rememberAsyncImagePainter(item.uri) else painterResource(
                        id = R.drawable.check_circle_) ,
                    contentDescription = "image"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Text that shows the name
                        Text(
                            text = item.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.robotomedium, FontWeight.Medium)),
                                fontSize = 18.sp,
                                color = Color.Black
                            ),

                        )

                        Icon(
                            painter = painterResource(id = if(item.isFavourite)R.drawable.ic_baseline_star_rate_24 else R.drawable.ic_baseline_star_outline_24),
                            contentDescription = "",
                            modifier = Modifier.size(35.dp)
                        )



                    }

                    Text(
                        modifier = Modifier
                            .padding(top = 2.dp),
                        text = "rate: ${item.popularity} ",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.robotothinitalic, FontWeight.Normal)),
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    )

                }
            }

        }
    }
}

