package com.example.blogapp.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachReversed
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.blogapp.R


@Composable
fun PostItemComponent(
    modifier : Modifier = Modifier
) {

}



@Composable
fun PostHeader(
    modifier: Modifier = Modifier,
    profilePicture: Int,
    profileName: String,
    location: String,
    postTime: String) {

    Surface (
        modifier = modifier.fillMaxWidth(),
        color = Color.Transparent
    ){

        Row (
            modifier = Modifier
                .width(255.dp),
            verticalAlignment = Alignment.CenterVertically
        ){


            Image(
                painter = painterResource(id = profilePicture),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape))

            Column (
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ){
                Text(
                    text = profileName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium)
                
                Text(
                    text = location,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall)
            }


            Text(
                text = postTime,
                style = MaterialTheme.typography.bodySmall)
        }
    }


}

@Composable
fun PostImage(modifier: Modifier = Modifier, postImage: String) {



    Surface (
        shape = MaterialTheme.shapes.large,
        shadowElevation = 10.dp,
        modifier = modifier
            .padding(vertical = 8.dp)
            .padding(start = 8.dp, end = 8.dp)
    ){

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(postImage)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop)
    }
}
@Composable
fun PostImageLocal(modifier: Modifier = Modifier, postImage: Int) {

    Surface (
        shape = MaterialTheme.shapes.large,
        shadowElevation = 10.dp,
        modifier = modifier
            .padding(vertical = 10.dp)
    ){
        Image(
            painter = painterResource(id = postImage),
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop)
    }

}


@Composable
fun PostFooter(modifier: Modifier = Modifier, likesCount: Long, commentsCount: Long) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null)

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = likesCount.toString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis)

        Spacer(modifier = Modifier.width(25.dp))

        Icon(
            imageVector = Icons.Filled.ModeComment,
            contentDescription = null)

        Spacer(modifier = Modifier.width(4.dp))

        Text(text = commentsCount.toString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PostLikesRow(
    usersImages: List<Painter>,
    usersNames: List<String>,
    limit :Int
) {

    val displayNames = if (usersNames.size >= 1) {
        usersNames.take(limit).joinToString(", ") + "..."
    } else {
        usersNames.joinToString("")
    }


    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        val offset = 20
        Box {

            val size = usersImages.size -1

            usersImages.asReversed().forEachIndexed { index, painter->

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .offset(x=(size - index).dp * offset)
                        .border(2.dp, Color.White, CircleShape)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

            }

        }


        val spaceDp = ((usersImages.size -1) * offset) + 4
        Spacer(modifier = Modifier.width((spaceDp).dp))

        Text(
            text = displayNames,
            style = MaterialTheme.typography.bodySmall
        )

    }

}
@Preview(showBackground = true, backgroundColor =  0xFFF5F0EE)
@Composable
fun PostItemComponentPreview() {

    val usersImages = listOf(
        painterResource(id = R.drawable.golden),
        painterResource(id = R.drawable.golden),
        painterResource(id = R.drawable.golden)

    )

    val usersNames = listOf(
        "Sasha", "Victor", "Natalia", "Juan"
    )

    Column(
        modifier = Modifier.padding(15.dp)
    ){
        PostHeader(
            profilePicture = R.drawable.golden,
            profileName = "Sasha",
            location = "Bogotá, Colombia",
            postTime = "2 hours ago")

        //PostImage(postImage = "https://www.akc.org/wp-content/uploads/2020/07/Golden-Retriever-puppy-standing-outdoors-500x486.jpg")

        PostImageLocal(postImage = R.drawable.golden)

        PostFooter(likesCount = 500, commentsCount = 600)

        Spacer(modifier = Modifier.height(10.dp))

        PostLikesRow(usersImages =usersImages , usersNames = usersNames, usersImages.size)
    }

}