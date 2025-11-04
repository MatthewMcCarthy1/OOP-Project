# OOP-Project: Simplifying Text with Word Embeddings and Virtual Threads

## Overview

This project is aimed at simplifying text using word embeddings and virtual threads. It includes a variety of components to handle different aspects of text processing, from loading word embeddings to calculating text similarity.

Note: I have not added the files for GloVe embeddings and Google 1000 as this may cause issues.

## Author

Matthew McCarthy (G00373167)

## Features

### 1. Text Simplification
The application simplifies text by replacing complex words with their simpler equivalents using the Google 1000 most common words list.

### 2. Concurrent Processing
Utilizes Java's virtual threads to perform concurrent file processing, enhancing performance and efficiency.

### 3. Word Embeddings
Loads and processes GloVe word embeddings to enable semantic text similarity calculations.

### 4. Customizable Input/Output
Allows users to specify custom file paths for embeddings, text inputs, and outputs.

### 5. Cosine Similarity
Employs cosine similarity calculations to find the most semantically similar words.

## Main Components

- **Runner**: The entry point of the application, providing a menu-driven interface for users to interact with.
- **GoogleWordsLoader**: Loads the Google 1000 words into a concurrent set.
- **TextProcessor**: Simplifies text by processing each line of the input file.
- **MapGoogle1000**: Manages word embeddings and performs similarity calculations.
- **SimilarityCalculator**: Calculates cosine similarity between word vectors.
- **FileProcessor**: Abstract class providing file processing utilities.
- **GloVEEmbeddingsLoader**: Loads GloVe word embeddings into a concurrent map.

## How to use
To run the application navigate to the .jar file and execute the following command:  
```sh
java --enable-preview -cp ./oop.jar ie.atu.sw.Runner  
```
A menu should pop up for you to enter your choices. 
Please note that for you to select option 5, you must specify all files in options 1-4. 
