# Handwritten Source Code Recognition For Python

This research project is based on the evaluation dataset of handwritten python source code smaples provided by the following study;

https://arxiv.org/abs/1706.00069

## There are two main parts to this research project

1.  Fine tunning Tesseract 4's [tessdata-best](https://github.com/tesseract-ocr/tessdata_best) LSTM-based language model by using tesseracts [training-tools](https://tesseract-ocr.github.io/tessdoc/tess4/TrainingTesseract-4.00.html) to recognize handwritten python code. The fine-tuned model was evaluated using the evaluation dataset.
* The training files used for training are saved in the _tesseract-training/training-data_ directory
* The tess-data files used for training are saved in the _tesseract-training/tess-data_ directory
* The least character-error model checkpoint is saved in the _tesseract-training/best-model-checkpoint_ directory
* The extracted LSTM model of the above checkpoint is saved in the _tesseract-training/best-model directory


2. Evaluating Google's [Digital INK Recognition](https://developers.google.com/ml-kit/vision/digital-ink-recognition) Engine's performance on the same evaluation dataset. The android application that recognizes each writing sample is **CodeGraphyEvalDataset**

    To use the application
    1. Chose Writing sample
    2. Chose Writer
    3. Recognize

## The Evaluation Dataset

The original dataset ( saved in _dataset/original-dataset_) presented by the study mentioned above is in the online-handwritting format ( x and y cordinate stroke data ). The author had to convert this data into individual images for each writing sample so it becomes compatible with Tesseract. The javascript code that does this is saved in _dataset/js-stroke2svg2jpeg_. The converted images can be found in _dataset/image-dataset_

The original stroke data had to be transformed so it can be easily loaded into the android application, that data is in the  _dataset/android-stroke-data_
directory

## Findings 

The following table depicts the Character Error Rate (CER) and Word Error Rate (WER) evaluations. It was found that the Digital Ink Model outperforms the fine-tuned tesseract model which shows improvement after fine tuning from tess-best (base tesseract) 
![image](https://user-images.githubusercontent.com/29557407/174560301-1302309b-1c3c-479d-84ce-7d79f12b2c62.png)

