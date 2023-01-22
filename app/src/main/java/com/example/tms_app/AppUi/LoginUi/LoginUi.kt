package com.example.tms_app.AppUi.LoginUi

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.TextFieldValue
import com.example.tms_app.ui.theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tms_app.R
import com.example.tms_app.Viewmodels.MainViewModel

@Composable
fun loginCard(){

}

@Composable
fun Login(mainViewModel: MainViewModel) {
    var userName by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isError by remember {
        mutableStateOf(false)
    }
    var isPressed by remember {
        mutableStateOf(false)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        // user name.
        stylishTextField(
            onValueChange = { userName = it },
            isError = isError,
            isPressed = isPressed,
            label = "user name",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = null,
                    Modifier.size(24.dp)
                )
            }
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        // password.
        stylishTextField(
            onValueChange = { password = it },
            isError = isError,
            isPressed = isPressed,
            label = "password",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.password),
                    contentDescription = null
                )
            },
            trailingIcon = { onIconClick, passwordVisibility ->
                Icon(
                    painter = if (passwordVisibility) painterResource(id = R.drawable.eye) else painterResource(
                        id = R.drawable.eye_off
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onIconClick() })
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = { if (it) VisualTransformation.None else PasswordVisualTransformation() }

        )

        Spacer(
            modifier = Modifier
                .padding(vertical = 40.dp)
        )
        OutlinedButton(
            onClick = { mainViewModel.login(userName, password) },Modifier.background(White)) {
            Text(text = "login")
        }

    }

}

@Composable
fun UserNameRow(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 5.dp, 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.user),
                contentDescription = null
            )
            Spacer(
                modifier = Modifier
                    .width(20.dp)
            )
            Text(
                text = name,
                fontSize = 14.sp,
                color = VeryDarkGrey
            )
        }
    }
}


// Trailing icon, when passed then clicking it will show or hide the text.
@Composable
fun stylishTextField(onValueChange: (newWord: String) -> Unit,
                     isError: Boolean = false,
                     isPressed: Boolean = false,
                     label: String = "",
                     leadingIcon:@Composable ()->Unit = {},
                     trailingIcon:@Composable (onIconClick: ()->Unit, textVisibility: Boolean) -> Unit = {_,_->},
                     keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
                     visualTransformation: (condition: Boolean) -> VisualTransformation = {VisualTransformation.None},
){
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(key1 = isPressed ){
        text = TextFieldValue("")
    }
    /*if(isError){
        text = TextFieldValue("")
        erase()
    }*/
    OutlinedTextField(
        value = text,
        isError = isError,
        leadingIcon = { leadingIcon() },
        trailingIcon = { trailingIcon({passwordVisible = !passwordVisible},passwordVisible) },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions, //KeyboardOptions(keyboardType = KeyboardType.Password)
        label = { Text(text = label) },
        onValueChange = {
            text = it
            onValueChange(it.text)
        },
        visualTransformation = visualTransformation(passwordVisible), //if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        colors =  TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = CloudyBlue,
            unfocusedBorderColor = VeryDarkGrey,
            focusedLabelColor = CloudyBlue,
            unfocusedLabelColor = VeryDarkGrey,
            leadingIconColor = CloudyBlue,
            disabledLeadingIconColor = VeryDarkGrey,
            backgroundColor = Color.Transparent,
            cursorColor = CloudyBlue,
            errorBorderColor = suspendy,
            errorLabelColor = suspendy,
            trailingIconColor = CloudyBlue,
            disabledTrailingIconColor = VeryDarkGrey,

            )
    )
}




