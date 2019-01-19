package de.grzb.medienkonvertierer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

@SpringBootApplication
@EnableBinding(Processor.class)
public class MedienKonvertiererApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedienKonvertiererApplication.class, args);
	}

	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public Medium konvertiere(String titel) {
		System.out.println("Konvertiere Medium: " + titel);
		Medium medium = new Medium();
		medium.setTitel(titel);
		return medium;
	}

	public static class Medium {
		String titel;

		public void setTitel(String titel)
		{
			this.titel = titel;
		}

		public String getTitel()
		{
			return titel;
		}

		@Override
		public String toString()
		{
			return this.titel;
		}
	}
}

