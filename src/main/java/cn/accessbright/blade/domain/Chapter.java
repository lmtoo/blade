package cn.accessbright.blade.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "t_chapter")
public class Chapter extends AbstractPersistable<Integer> {

	private String name;

	private Image image;

	@Enumerated(EnumType.STRING)
	private Switch status;

	private Integer questionCount;

	private Long peroid;

	private Integer rightCount;

	@OneToMany(mappedBy="chapter")
	private List<Barrier> barriers = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Switch getStatus() {
		return status;
	}

	public void setStatus(Switch status) {
		this.status = status;
	}

	public Integer getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}

	public Long getPeroid() {
		return peroid;
	}

	public void setPeroid(Long peroid) {
		this.peroid = peroid;
	}

	public Integer getRightCount() {
		return rightCount;
	}

	public void setRightCount(Integer rightCount) {
		this.rightCount = rightCount;
	}

	public List<Barrier> getBarriers() {
		return barriers;
	}

	public void setBarriers(List<Barrier> barriers) {
		this.barriers = barriers;
	}
}
