package sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import reflection.ReflectionHelper;

public class SaxHandler extends DefaultHandler {
	private static String CLASSNAME = "class";
	private String element = null;
	private Object object = null;

	// вызывается при начале парсинга документа
	public void startDocument() throws SAXException {
		System.out.println("Start document");
	}

	public void endDocument() throws SAXException {
		System.out.println("End document ");
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(!qName.equals(CLASSNAME)){
			element = qName;
		}
		else {
			// Получаем имя класса из атрибутов элемента
			String className = attributes.getValue(0);
			System.out.println("Class name: " + className);
			object = ReflectionHelper.createInstance(className);
		}
	}

	// Метод вызывается при завершении элемента
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// Сбрасываем текущий элемент
		element = null;
	}

	// Метод вызывается для обработки текста внутри элемента
	public void characters(char ch[], int start, int length) throws SAXException {
		// Если есть текущий элемент
		if(element != null){
			// Преобразуем символы в строку
			String value = new String(ch, start, length);
			System.out.println(element + " = " + value);
			// Устанавливаем значение поля объекта с помощью ReflectionHelper
			ReflectionHelper.setFieldValue(object, element, value);
		}
	}

	public Object getObject(){
		return object;
	}
}
