<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<button class="btn btn-secondary" onclick="history.back()">Back</button>
	<c:if test="${board.user.id == principal.user.id}">
		<a href="/board/${board.id}/updateForm" class="btn btn-warning">Edit</a>
		<button id="btn-delete" class="btn btn-danger">Delete</button>
	</c:if>
	<br /> <br />
	<div>
		Post id: <span id="id"><i>${board.id} </i></span> User: <span><i>${board.user.username} </i></span>
	</div>
	<br />
	<div>
		<h3>${board.title}</h3>
	</div>
	<hr />
	<div>
		<div>${board.content}</div>
	</div>
	<hr />


	<div class="card">
		<div class="card-body">
			<textarea class="form-control" row="1"></textarea>
		</div>
		<div class="card-footer">
			<button class="btn btn-primary">Comment</button>
		</div>
	</div>
	<br />
	<div class="card">
		<div class="card-header">Comments</div>
		<ul id="comment--box" class="list-group">
			<li id="comment--1" class="list-group-item d-flex justify-content-between">
				<div>Something</div>
				<div class="d-flex">
					<div class="font-italic">By: &nbsp;</div>
					<button class="badge"'>Delete</button>
				</div>
			</li>
		</ul>
	</div>

</div>

<script>
      $('.summernote').summernote({
        tabsize: 2,
        height: 300
      });
    </script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>




