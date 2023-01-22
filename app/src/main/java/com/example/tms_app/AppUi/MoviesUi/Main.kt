package com.example.tms_app.AppUi.MoviesUi

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tms_app.EnumClasses.CurrentTab
import com.example.tms_app.AppUi.LoginUi.Login
import com.example.tms_app.ui.theme.CloudyBlue
import kotlinx.coroutines.launch
import com.example.tms_app.R
import com.example.tms_app.AppUi.TvUI.TopRatedTvs
import com.example.tms_app.AppUi.previewDetails
import com.example.tms_app.MainUi.TopRatedMovies
import com.example.tms_app.MainUi.searchResult
import com.example.tms_app.Viewmodels.MainViewModel
import com.example.tms_app.Viewmodels.SearchViewModel
import com.example.tms_app.Viewmodels.TvViewModel


@Composable
fun StartApp(mainViewModel: MainViewModel, tvViewModel: TvViewModel, searchViewModel: SearchViewModel){
    val isLogged by mainViewModel.isLogged.collectAsState()
    when(isLogged) {
        false -> {
            Login(mainViewModel)
        }
        true -> {
            Home(mainViewModel, tvViewModel, searchViewModel)
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(mainViewModel: MainViewModel, tvViewModel: TvViewModel, searchViewModel: SearchViewModel){
    val coroutineScope = rememberCoroutineScope()
    var scaffoldState = rememberBackdropScaffoldState(
        BackdropValue.Concealed, spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    var currentTab: CurrentTab by remember {
        mutableStateOf(CurrentTab.Movies)
    }
    var userName: String by remember {
        mutableStateOf("")
    }
    val movieWithDetails by mainViewModel.MovieWithDetail.collectAsState()
    LaunchedEffect(key1 = true){
        userName = mainViewModel.getUserName()
    }
    Scaffold(
        drawerContent = {
            DrawerContent(
                itemClick = {
                    coroutineScope.launch {
                        scaffoldState.conceal()
                    }
                },
                changeTab = {newTab ->
                    currentTab = newTab
                },
                onSearch = {query -> searchViewModel.search(query)
                },
                onExit = {
                   /* mainViewModel.logOut()*/
                },
                userName = userName
            )
        },

        // to detect what should happen when we touch the drawer item
        content = {
            when(currentTab) {
                CurrentTab.Movies -> {
                    TopRatedMovies(mainViewModel, { newTab -> currentTab = newTab }
                    ) { movieId ->
                        coroutineScope.launch {
                            mainViewModel.getMovieDetailsByNetwork(movieId)
                        }

                    }
                }// if we touch top rated movies
                CurrentTab.Series -> {  // if we touch top rated series
                    TopRatedTvs(tvViewModel)
                }
                CurrentTab.Custom -> { // this when we app
                    searchResult(mainViewModel, tvViewModel, searchViewModel)
                }
                CurrentTab.Login -> {
                    Login(mainViewModel)
                }
                CurrentTab.Details -> {
                    if (movieWithDetails != null) {
                        previewDetails(
                            movieWithDetails!!.poster_path.toString(),
                            movieWithDetails!!.title,
                            movieWithDetails!!.release_date,
                            movieWithDetails!!.popularity.toString(),
                            movieWithDetails!!.runtime.toString(),
                            movieWithDetails!!.overview.toString(),
                            movieWithDetails!!.status_message,
                            movieWithDetails!!.budget.toString(),
                            movieWithDetails!!.vote_count.toString()
                        )
                    }
                }
                else -> {}

            }

        }
    )
}





@Composable
fun SingleRow(title: String, icon: Painter, information: String){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(0.dp, 8.dp)
            .heightIn(100.dp, 30.dp)
            .width(600.dp)

    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
                .size(22.dp)
                .padding(end = 6.dp, start = 2.dp),
            tint = CloudyBlue
        )
        Text(
            text = title,
            color = CloudyBlue,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .width(100.dp),
            style = TextStyle(fontSize = 15.sp),
            fontWeight = FontWeight.Bold

        )
        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
            Text(
                text = information,
                color = Color.Black,
                style = TextStyle(fontSize = 12.sp),
                textAlign = TextAlign.Start
            )
        }

    }
}



@Composable
fun DrawerContent(
    gradientColors: List<Color> = listOf(Color(0xff536c8c), Color(0xffa5c8dc)),
    itemClick: () -> Unit,
    changeTab: (newTab: CurrentTab) -> Unit,
    onSearch: (query: String) -> Unit,
    onExit: () -> Unit,
    userName: String
) {

    val itemsList = prepareNavigationDrawerItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColors)),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 36.dp)
    ) {

        item {

            // user's image
            Image(
                modifier = Modifier
                    .size(size = 120.dp)
                    .clip(shape = CircleShape),
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Profile Image"
            )

            // user's name
            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = userName,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )


        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item{
            SearchAppBar(onSearch){
                changeTab(CurrentTab.Custom)
                itemClick()
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

        items(itemsList) { item ->
            NavigationListItem(item = item) {
                itemClick()
                when(item.type){
                    CurrentTab.Movies ->{changeTab(CurrentTab.Movies)}
                    CurrentTab.Series -> {changeTab(CurrentTab.Series)}
                    CurrentTab.Login -> {changeTab(CurrentTab.Login)}
                    CurrentTab.Logout -> {onExit()}
                    else -> {}
                }

            }
        }
    }
}

@Composable
private fun NavigationListItem(
    item: NavigationDrawerItem,
    unreadBubbleColor: Color = Color(0xFF0FFF93),
    itemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                itemClick()
            }
            .padding(horizontal = 14.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // icon and unread bubble
        Box {

            Icon(
                modifier = Modifier
                    .padding(all = if (item.showUnreadBubble && item.label == "Messages") 5.dp else 2.dp)
                    .size(size = if (item.showUnreadBubble && item.label == "Messages") 24.dp else 28.dp),
                painter = item.image,
                contentDescription = null,
                tint = Color.White
            )

            // unread bubble
            if (item.showUnreadBubble) {
                Box(
                    modifier = Modifier
                        .size(size = 8.dp)
                        .align(alignment = Alignment.TopEnd)
                        .background(color = unreadBubbleColor, shape = CircleShape)
                )
            }
        }

        // label
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = item.label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

@Composable
private fun prepareNavigationDrawerItems(): List<NavigationDrawerItem> {
    val itemsList = arrayListOf<NavigationDrawerItem>()

    itemsList.add(
        NavigationDrawerItem(
            image = painterResource(id = R.drawable.settings),
            label = "Top rated movies",
            type = CurrentTab.Movies
        )
    )
    itemsList.add(
        NavigationDrawerItem(
            image = painterResource(id = R.drawable.log_out),
            label = "Top rated Series",
            type = CurrentTab.Series
        )
    )

    return itemsList
}

data class NavigationDrawerItem(
    val image: Painter,
    val label: String,
    val showUnreadBubble: Boolean = false,
    val type: CurrentTab
)


@Composable
private fun SearchAppBar(onSearch: (query: String)->Unit, changeTab: (newTab: CurrentTab) -> Unit) {
    // Immediately update and keep track of query from text field changes.
    var query: String by rememberSaveable { mutableStateOf("") }
    var showClearIcon by rememberSaveable { mutableStateOf(false) }

    if (query.isEmpty()) {
        showClearIcon = false
    } else if (query.isNotEmpty()) {
        showClearIcon = true
    }

    TextField(
        value = query,
        onValueChange = { onQueryChanged ->
            // If user makes changes to text, immediately updated it.
            query = onQueryChanged
            // To avoid crash, only query when string isn't empty.
           /* if (onQueryChanged.isNotEmpty()) {
                // Pass latest query to refresh search results.
                viewModel.performQuery(onQueryChanged)
            }*/
        },
        leadingIcon = {
            IconButton(onClick = {
                onSearch(query)
                changeTab(CurrentTab.Custom)
            }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    tint = MaterialTheme.colors.onBackground,
                    contentDescription = "Search Icon"
                )
            }
        },
        trailingIcon = {
            if (showClearIcon) {
                IconButton(onClick = { query = "" }) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = "Clear Icon"
                    )
                }
            }
        },
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        placeholder = { Text(text = "search") },
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent, shape = RectangleShape)
            .padding(horizontal = 10.dp)
    )
}


