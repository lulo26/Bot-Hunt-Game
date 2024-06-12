B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=12.8
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: true
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private LblLeadership As Label
	Private BtnVolver As Button
	Private LblLevel As Label
	Private SpnrLevels As Spinner
	Private BtnCheck As Button
	Private LblLeadershipShadow As Label
	Private TableScores As B4XTable
End Sub

Sub Activity_Create(FirstTime As Boolean)
	
	
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("FrmLeadership")
	If Functions.Spanish = True Then
		'tabla en español
		TableScores.AddColumn ("Jugador",TableScores.COLUMN_TYPE_TEXT)
		TableScores.AddColumn ("Puntaje", TableScores.COLUMN_TYPE_NUMBERS)
		'spinner
		SpnrLevels.Clear
		SpnrLevels.Add("Fácil")
		SpnrLevels.Add("Intermedio")
		SpnrLevels.Add("Difícil")
		'otras vainas en español
		LblLeadership.Text = "Puntajes"
		LblLeadershipShadow.Text = "Puntajes"
		LblLevel.Text = "Nivel:"
		BtnCheck.Text = "Ver"
	Else
		'table english
		TableScores.AddColumn ("Player",TableScores.COLUMN_TYPE_TEXT)
		TableScores.AddColumn ("Score", TableScores.COLUMN_TYPE_NUMBERS)
		'spinner
		SpnrLevels.Clear
		SpnrLevels.Add("Easy")
		SpnrLevels.Add("Medium")
		SpnrLevels.Add("Hard")
		
		'labels and stuff english
		LblLeadership.Text = "Leadership"
		LblLeadershipShadow.Text = "Leadership"
		LblLevel.Text = "Level:"
		BtnCheck.Text = "Check"
	End If
End Sub

Sub Activity_Resume
	If Functions.Spanish = True Then
		'spinner
		SpnrLevels.Clear
		SpnrLevels.Add("Fácil")
		SpnrLevels.Add("Intermedio")
		SpnrLevels.Add("Difícil")
		'otras vainas en español
		LblLeadership.Text = "Puntajes"
		LblLeadershipShadow.Text = "Puntajes"
		LblLevel.Text = "Nivel:"
		BtnCheck.Text = "Ver"
	Else
		'spinner
		SpnrLevels.Clear
		SpnrLevels.Add("Easy")
		SpnrLevels.Add("Medium")
		SpnrLevels.Add("Hard")
		
		'labels and stuff english
		LblLeadership.Text = "Leadership"
		LblLeadershipShadow.Text = "Leadership"
		LblLevel.Text = "Level:"
		BtnCheck.Text = "Check"
	End If
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub BtnVolver_Click
	StartActivity("FrmOptions")
End Sub

Private Sub BtnCheck_Click
	Dim ListScores As List
	Dim newListScores As List
	newListScores.Initialize
	If File.Exists(File.DirInternal, "GameData.txt") Then
		ListScores = File.ReadList(File.DirInternal,"GameData.txt")
		For Each Line In ListScores
			Dim values As String = Line
			Dim arrayList() As String = Regex.Split(",",values)
			newListScores.Add(Array(arrayList(0), arrayList(4)))
		Next
		TableScores.SetData(newListScores)
	End If
End Sub