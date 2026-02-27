# OOP-Project: Text Simplifier with Virtual Threads

## Overview

This project is a high-performance text simplification tool built with **Java 25** and **Virtual Threads (Project Loom)**. It demonstrates modern concurrency patterns by using `StructuredTaskScope` to process text files in parallel, replacing complex words with simpler alternatives based on GloVe word embeddings and the Google 1000 most common words list.

## Author

**Matthew McCarthy** (G00373167)

## Features

- **Virtual Threads & Structured Concurrency:** Leverages Java's latest concurrent APIs (`StructuredTaskScope`) for high-throughput, non-blocking I/O operations.
- **Word Embeddings:** Uses pre-trained GloVe vectors to calculate semantic similarity and find the best simple synonyms.
- **Parallel Processing:** Simplifies large text files efficiently by processing lines concurrently while maintaining output order.
- **Vector Similarity:** Implements Cosine Similarity to mathematically determine the closeness of word meanings.
- **Plug-and-Play:** Includes necessary embedding data and word lists for immediate execution.

## Prerequisites

- **Java Development Kit (JDK) 25** (or a recent EA build supporting the latest preview features).
- The `--enable-preview` flag must be used during both compilation and execution.

## Installation & Usage

### 1. Clone the Repository
```sh
git clone https://github.com/your-username/OOP-Project.git
cd OOP-Project
```

### 2. Compile the Project
Compile the source code, enabling preview features for virtual threads:
```sh
mkdir -p bin
javac -d bin --enable-preview --release 25 src/ie/atu/sw/*.java
```

### 3. Run the Application
Execute the compiled bytecode. You can start the interactive runner directly:
```sh
java --enable-preview -cp bin ie.atu.sw.Runner
```

### 4. Application Menu
Once running, follow the on-screen menu:
1.  **Specify Embeddings File:** Enter `embeddings.txt` (included).
2.  **Specify Google 1000 File:** Enter `google-1000.txt` (included).
3.  **Specify Text File:** Enter the path to your input text (e.g., `test.txt`).
4.  **Specify Output File:** Enter the desired output path (e.g., `out.txt`).
5.  **Execute:** Watch the simplification happen in parallel!

## Main Components

- **`Runner`**: The interactive CLI entry point.
- **`TextProcessor`**: The core engine that orchestrates parallel line processing using virtual threads.
- **`FileProcessor`**: An abstract base class handling safe, sequential I/O operations.
- **`MapGoogle1000`**: Manages the mapping between complex words and their simple equivalents.
- **`SimilarityCalculator`**: Performs the vector math (cosine similarity).
- **`GloVEEmbeddingsLoader`**: Efficiently loads high-dimensional vector data into concurrent maps.

---
*Developed for the Object Oriented Programming module at ATU. Improvements have been made after submission.*
