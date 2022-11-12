package com.plcoding.newphotopickerandroid13

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.plcoding.newphotopickerandroid13.ui.theme.NewPhotoPickerAndroid13Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewPhotoPickerAndroid13Theme {
                var selectedImageUri by remember {
                    mutableStateOf<Uri?>(null)
                }
                var selectedImageUris by remember {
                    mutableStateOf<List<Uri>>(emptyList())
                }
                val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                    onResult = { uri -> selectedImageUri = uri }
                )
                val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickMultipleVisualMedia(),
                    onResult = { uris -> selectedImageUris = uris }
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Button(onClick = {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }) {
                                Text(text = "Pick one photo")
                            }
                            Button(onClick = {
                                multiplePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }) {
                                Text(text = "Pick multiple photo")
                            }
                        }
                    }

                    item {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    items(selectedImageUris) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}