package cn.accessbright.blade.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "t_question")
public class Question extends AbstractPersistable<Integer> {

	@ManyToOne
	private Image image;

	private String description;

	private String question;

	private String rightOptions;

	private String wrongOptions;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getRightOptions() {
		return rightOptions;
	}

	public void setRightOptions(String rightOptions) {
		this.rightOptions = rightOptions;
	}

	public String getWrongOptions() {
		return wrongOptions;
	}

	public void setWrongOptions(String wrongOptions) {
		this.wrongOptions = wrongOptions;
	}
}
