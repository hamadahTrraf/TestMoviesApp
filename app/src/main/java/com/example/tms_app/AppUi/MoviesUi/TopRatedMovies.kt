package com.example.tms_app.MainUi

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.tms_app.EnumClasses.CurrentTab
import com.example.tms_app.Network.Response.MovieDetails
import com.example.tms_app.R
import com.example.tms_app.Viewmodels.MainViewModel
import kotlinx.coroutines.Job

@Composable
fun TopRatedMovies(viewModel: MainViewModel, changeTab: (newTab: CurrentTab)-> Unit, onDetails: (movieId: Int)->Unit) {
    LaunchedEffect(key1 = false){
        viewModel.emptyMoviesFromDatyaBase()
        viewModel.getTopRatedMovies(1)
    }
    val movies by viewModel.movies.collectAsState()
    LazyColumn{
        /*items(movies){item->
            UserNameRow(item)
        }*/
        items(movies){
            item->
            if(item != null){
                MovieItemToShow(
                    MovieInitialInfo(
                        id = item.id,
                        image = R.drawable.user,
                        title = item.title,
                        popularity = item.popularity.toString(),
                        release_date = item.release_date,
                        isFavourite = item.isFavourite,
                        uri = if(item.poster_path != null) "https://image.tmdb.org/t/p/w500/${item.poster_path}" else null
                        ),
                    onStarClick = {newState->
                    if(newState){
                        viewModel.setFavouriteOn(item.id,"movie")
                    }
                    else{
                        viewModel.setFavouriteOff(item.id,"movie")
                    }
                },
                    onDetails = onDetails,
                    onChangeTab = changeTab
                )
            }
        }
    }

}




@Composable
fun MovieItemToShow(
    item: MovieInitialInfo,
    context: Context = LocalContext.current.applicationContext,
    onStarClick:(Boolean)->Unit,
    onDetails: (movieId: Int)-> Unit,
    onChangeTab: (newTab: CurrentTab)-> Unit
) {

    Box(
        modifier = Modifier
            .clickable(
                onClick = {
                    onDetails(item.id)
                    onChangeTab(CurrentTab.Details)
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
                        id = R.drawable.check_circle_
                    ),
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
                            text = item.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.robotomedium, FontWeight.Medium)),
                                fontSize = 18.sp,
                                color = Color.Black
                            ),

                        )
                        IconButton(onClick = {onStarClick(!item.isFavourite)}) {
                            Icon(
                                painter = painterResource(id = if(item.isFavourite)R.drawable.ic_baseline_star_rate_24 else R.drawable.ic_baseline_star_outline_24),
                                contentDescription = "",
                                modifier = Modifier.size(35.dp)
                            )
                        }

                    }

                    Text(
                        modifier = Modifier
                            .padding(top = 2.dp),
                        text = "rate: ${item.popularity} | filmed in: ${item.release_date}",
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





